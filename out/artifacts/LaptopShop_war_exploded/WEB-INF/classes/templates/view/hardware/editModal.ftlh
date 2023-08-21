<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import '../../errorsHandlers/valueConsistentBreaker.ftlh' as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro hardwareEdit id currentAssemblyName currentCpuModel currentGpuModel currentDisplayModel
currentRamModel currentSsdModel currentHddModel currentPrice>
    <div class="modal fade" id="hardwareEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="hardwareEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="hardwareEditLabel${id}">Изменить информацию о сборке</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/hardware/edit/${id}"
                          id="hardwareEditForm${id}" class="form-group needs-validation w-100" novalidate
                          onsubmit="return validateForm${id}()">
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="editAssemblyNameInput${id}">
                                    Название изменяемой сборки:
                                </label>
                                <input type="text" class="form-control mr-1 mt-2" id="editAssemblyNameInput${id}"
                                       name="editAssemblyName" required/>
                                <div class="invalid-feedback">Название изменяемой сборки задано некорректно</div>
                            </div>
                        </div>

                        <#if cpuModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="cpuSelect${id}">Модель процессора:</label>
                                    <select class="custom-select" id="cpuSelect${id}" name="editCpuModel" required>
                                        <#list cpuModels as cpuModel>
                                            <option value="${cpuModel}">${cpuModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель процессора</div>
                                </div>
                            </div>
                        </#if>

                        <#if gpuModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="gpuSelect${id}">Модель видеокарты:</label>
                                    <select class="custom-select" id="gpuSelect${id}" name="editGpuModel" required>
                                        <#list gpuModels as gpuModel>
                                            <option value="${gpuModel}">${gpuModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель видеокарты</div>
                                </div>
                            </div>
                        </#if>

                        <#if displayModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="displaySelect${id}">Модель дисплея:</label>
                                    <select class="custom-select" id="displaySelect${id}" name="editDisplayModel"
                                            required>
                                        <#list displayModels as displayModel>
                                            <option value="${displayModel}">${displayModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель дисплея</div>
                                </div>
                            </div>
                        </#if>

                        <#if ramModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="ramSelect${id}">Модель оперативной
                                        памяти:</label>
                                    <select class="custom-select" id="ramSelect${id}" name="editRamModel" required>
                                        <#list ramModels as ramModel>
                                            <option value="${ramModel}">${ramModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель оперативной памяти</div>
                                </div>
                            </div>
                        </#if>

                        <#if ssdModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="ssdSelect${id}">Модель SSD диска:</label>
                                    <select class="custom-select" id="ssdSelect${id}" name="editSsdModel" required>
                                        <#list ssdModels as ssdModel>
                                            <option value="${ssdModel}">${ssdModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель SSD диска</div>
                                </div>
                            </div>
                        </#if>

                        <#if hddModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="hddSelect${id}">Модель HDD диска:</label>
                                    <select class="custom-select" id="hddSelect${id}" name="editHddModel" required>
                                        <#list hddModels as hddModel>
                                            <option value="${hddModel}">${hddModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель HDD диска</div>
                                </div>
                            </div>
                        </#if>
                        <div class="form-row my-2">
                            <div class="col">
                            <label class="d-inline ml-2" for="price">Цена:</label>
                            <input type="number" id="editPriceInput${id}" min="1" max="15000" class="form-control"
                                   name="editPrice" required/>
                            <div class="invalid-feedback">Некорректная цена</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editAssemblyNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editAssemblyNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="hardwareEditModal${id}" formId="hardwareEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#hardwareEditModal${id}").on("show.bs.modal", function () {
                            $("#editAssemblyNameInput${id}").val("${currentAssemblyName}").change();
                            $("#cpuSelect${id}").val("${currentCpuModel}").change();
                            $("#gpuSelect${id}").val("${currentGpuModel}").change();
                            $("#displaySelect${id}").val("${currentDisplayModel}").change();
                            $("#ramSelect${id}").val("${currentRamModel}").change();
                            $("#ssdSelect${id}").val("${currentSsdModel}").change();
                            $("#hddSelect${id}").val("${currentHddModel}").change();
                            $("#editPriceInput${id}").val("${currentPrice}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="hardwareEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>