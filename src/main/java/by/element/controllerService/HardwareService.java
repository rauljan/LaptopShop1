package by.element.controllerService;

import by.element.model.Hardware;
import by.element.excelInteraction.imports.HardwareExcelImporter;
import by.element.repos.*;
import by.element.specification.HardwareSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
public class HardwareService {
    private final HardwareRepo hardwareRepo;

    private final CPURepo cpuRepo;
    private final RAMRepo ramRepo;
    private final SSDRepo ssdRepo;
    private final DisplayRepo displayRepo;
    private final HDDRepo hddRepo;
    private final GPURepo gpuRepo;

    private final HardwareExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public HardwareService(HardwareRepo hardwareRepo, CPURepo cpuRepo, RAMRepo ramRepo,
                           SSDRepo ssdRepo, DisplayRepo displayRepo, HDDRepo hddRepo,
                           GPURepo gpuRepo, HardwareExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.hardwareRepo = hardwareRepo;
        this.cpuRepo = cpuRepo;
        this.ramRepo = ramRepo;
        this.ssdRepo = ssdRepo;
        this.displayRepo = displayRepo;
        this.hddRepo = hddRepo;
        this.gpuRepo = gpuRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Hardware> loadHardwareTable(String displayModel, String displayDiagonal, String displayResolution,
                                                String displayType, String cpuModel, String cpuFrequency, String ramModel,
                                                Integer ramMemory, String ssdModel, Integer ssdMemory, String hddModel,
                                                Integer hddMemory, String gpuModel, Integer gpuMemory, String assemblyName,
                                                Integer price,
                                                Model model) {
        var hardwareSpecification = createHardwareSpecification(displayModel, displayDiagonal, displayResolution,
                displayType, cpuModel, cpuFrequency, ramModel, ramMemory, ssdModel, ssdMemory, hddModel, hddMemory,
                gpuModel, gpuMemory, price, assemblyName);
        var hardware = hardwareRepo.findAll(hardwareSpecification);
        initializeHardwareChoices(model);
        return hardware;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Hardware> createHardwareSpecification(String displayModel, String displayDiagonal,
                                                                String displayResolution, String displayType,
                                                                String cpuModel, String cpuFrequency, String ramModel,
                                                                Integer ramMemory, String ssdModel, Integer ssdMemory,
                                                                String hddModel, Integer hddMemory, String gpuModel,
                                                                Integer gpuMemory, Integer price, String assemblyName) {
        return Specification
                .where(HardwareSpecification.displayModelLike(displayModel)).and(HardwareSpecification.displayDiagonalEqual(displayDiagonal))
                .and(HardwareSpecification.displayResolutionEqual(displayResolution)).and(HardwareSpecification.displayTypeEqual(displayType))
                .and(HardwareSpecification.cpuModelLike(cpuModel)).and(HardwareSpecification.cpuFrequencyEqual(cpuFrequency))
                .and(HardwareSpecification.gpuModelLike(gpuModel)).and(HardwareSpecification.gpuMemoryEqual(gpuMemory))
                .and(HardwareSpecification.ramModelLike(ramModel)).and(HardwareSpecification.ramMemoryEqual(ramMemory))
                .and(HardwareSpecification.ssdModelLike(ssdModel)).and(HardwareSpecification.ssdMemoryEqual(ssdMemory))
                .and(HardwareSpecification.hddModelLike(hddModel)).and(HardwareSpecification.hddMemoryEqual(hddMemory))
                .and(HardwareSpecification.assemblyNameEqual(assemblyName))
                .and(HardwareSpecification.priceEqual(price));
    }

    public boolean addHardwareRecord(String assemblyName, String cpuModel, String ramModel, String ssdModel,
                                     String displayModel, String hddModel, String gpuModel, Integer price, Model model) {
        var cpu = cpuRepo.findByModel(cpuModel);
        var ram = ramRepo.findByModel(ramModel);
        var ssd = ssdRepo.findByModel(ssdModel);
        var hdd = hddRepo.findByModel(hddModel);
        var gpu = gpuRepo.findByModel(gpuModel);
        var display = displayRepo.findByModel(displayModel);
        var newHardware = new Hardware(assemblyName, cpu, gpu, ram, ssd, hdd, price, display);
        initializeHardwareChoices(model);
        return saveRecord(newHardware);
    }

    public boolean editHardwareRecord(String editAssemblyName, String editCpuModel, String editRamModel,
                                      String editSsdModel, String editDisplayModel, String editHddModel,
                                      String editGpuModel, Integer editPrice,
                                      @NotNull Hardware editHardware,
                                      Model model) {
        editHardware.setAssemblyName(editAssemblyName);
        var cpu = cpuRepo.findByModel(editCpuModel);
        editHardware.setCpu(cpu);
        var gpu = gpuRepo.findByModel(editGpuModel);
        editHardware.setGpu(gpu);
        var ram = ramRepo.findByModel(editRamModel);
        editHardware.setRam(ram);
        var ssd = ssdRepo.findByModel(editSsdModel);
        editHardware.setSsd(ssd);
        var hdd = hddRepo.findByModel(editHddModel);
        editHardware.setHdd(hdd);
        var display = displayRepo.findByModel(editDisplayModel);
        editHardware.setDisplay(display);
        editHardware.setPrice(editPrice);
        initializeHardwareChoices(model);
        return saveRecord(editHardware);
    }

    public boolean importExcelRecords(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        initializeHardwareChoices(model);
        var hardwareFilePath = "";
        try {
            hardwareFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newHardware = excelImporter.importFile(hardwareFilePath);
            newHardware.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(hardwareFilePath);
            return false;
        }
    }

    private boolean saveRecord(Hardware saveHardware) {
        try {
            hardwareRepo.save(saveHardware);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Hardware delHardware) {
        hardwareRepo.delete(delHardware);
    }

    private void initializeHardwareChoices(@NotNull Model model) {
        model.addAttribute("cpuModels", cpuRepo.getAllModels())
                .addAttribute("ramModels", ramRepo.getAllModels())
                .addAttribute("ssdModels", ssdRepo.getAllModels())
                .addAttribute("displayModels", displayRepo.getAllModels())
                .addAttribute("hddModels", hddRepo.getAllModels())
                .addAttribute("gpuModels", gpuRepo.getAllModels());
    }
}