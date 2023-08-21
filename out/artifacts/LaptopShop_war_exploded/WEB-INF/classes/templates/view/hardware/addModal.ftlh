<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro hardwareAdd>
    <div class="modal fade" id="hardwareAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="hardwareAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="hardwareAddLabel">Добавить новую сборку</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if cpuModels?has_content>
                    <#if ramModels?has_content>
                        <#if ssdModels?has_content>
                            <#if displayModels?has_content>
                                <#if hddModels?has_content>
                                    <#if gpuModels?has_content>
                                        <div class="modal-body">
                                            <form method="post" id="hardwareAddForm" action="/hardware/add"
                                                  class="form-group needs-validation w-100"
                                                  novalidate onsubmit="return validateFormAdd()">
                                                <div class="form-row mb-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="assemblyName">
                                                            Название новой сборки:</label>
                                                        <input type="text" id="newAssemblyNameInput"
                                                               class="form-control" name="assemblyName"
                                                               required/>
                                                        <div class="invalid-feedback">
                                                            Название новой сборки задано некорректно
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="cpuModel">
                                                            Модель процессора:</label>
                                                        <select class="custom-select" id="newCpuSelect" name="cpuModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list cpuModels as cpuModel>
                                                                <option>${cpuModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не выбрана модель процессора</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="gpuModel">
                                                            Модель видеокарты:</label>
                                                        <select class="custom-select" id="newGpuSelect" name="gpuModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list gpuModels as gpuModel>
                                                                <option>${gpuModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не выбрана модель видеокарты</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="displayModel">
                                                            Модель дисплея:</label>
                                                        <select class="custom-select" id="newDisplaySelect"
                                                                name="displayModel" required>
                                                            <option value="" selected disabled></option>
                                                            <#list displayModels as displayModel>
                                                                <option>${displayModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не выбрана модель дисплея</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="ramModel">
                                                            Модель оперативной памяти:</label>
                                                        <select class="custom-select" id="newRamSelect" name="ramModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list ramModels as ramModel>
                                                                <option>${ramModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не выбрана модель оперативной
                                                            памяти
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="ssdModel">
                                                            Модель SSD диска:</label>
                                                        <select class="custom-select" id="newSsdSelect" name="ssdModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list ssdModels as ssdModel>
                                                                <option>${ssdModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не выбрана модель SSD диска</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="ssdModel">
                                                            Модель HDD диска:</label>
                                                        <select class="custom-select" id="newHddSelect" name="hddModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list hddModels as hddModel>
                                                                <option>${hddModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не выбрана модель HDD диска</div>
                                                    </div>
                                                </div>
                                                <div class="form-row mn-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="price">Цена:</label>
                                                        <input type="number" id="newPriceInput" min="1" max="15000" class="form-control"
                                                               name="price" required/>
                                                        <div class="invalid-feedback">Некорректная цена</div>
                                                    </div>
                                                </div>
<#--                                                TODO Fix bug with anonymous user-->
                                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            </form>
                                            <@eB.emptyBreaker inputIds=["newAssemblyNameInput"]/>
                                            <@vC.valueBreaker id="Add" inputIds=["newAssemblyNameInput"]/>
                                            <@fC.inputsCleaner modalId="hardwareAddModal" inputIds=["newAssemblyNameInput"]/>
                                            <@sC.selectsCleaner modalId="hardwareAddModal"
                                            selectIds=["newCpuSelect", "newGpuSelect", "newDisplaySelect",
                                            "newRamSelect", "newSsdSelect", "newHddSelect", "newPriceInput"]/>
                                            <@fSc.fieldsStatusCleaner modalId="hardwareAddModal" formId="hardwareAddForm"/>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить
                                            </button>
                                            <button type="submit" form="hardwareAddForm" class="btn btn-danger">Добавить
                                            </button>
                                        </div>
                                    <#else>
                                        <div class="modal-body">
                                            <div class="alert alert-warning" role="alert">
                                                <div class="h1 text-center">Не найдено ни одной модели видеокарты.</div>
                                                <div class="h3 text-center">
                                                    <a href="/gpu">Добавьте новую видеокарту</a> для создания сборки.
                                                </div>
                                            </div>
                                        </div>
                                    </#if>
                                <#else>
                                    <div class="modal-body">
                                        <div class="alert alert-warning" role="alert">
                                            <div class="h1 text-center">Не найдено ни одной модели HDD диска.</div>
                                            <div class="h3 text-center">
                                                <a href="/hdd">Добавьте новый HDD диск</a> для создания сборки.
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                            <#else>
                                <div class="modal-body">
                                    <div class="alert alert-warning" role="alert">
                                        <div class="h1 text-center">Не найдено ни одной модели дисплея.</div>
                                        <div class="h3 text-center">
                                            <a href="/display">Добавьте новый дисплей</a> для создания сборки.
                                        </div>
                                    </div>
                                </div>
                            </#if>
                        <#else>
                            <div class="modal-body">
                                <div class="alert alert-warning" role="alert">
                                    <div class="h1 text-center">Не найдено ни одной модели SSD диска.</div>
                                    <div class="h3 text-center">
                                        <a href="/ssd">Добавьте новый SSD диск</a> для создания сборки.
                                    </div>
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не найдено ни одной модели оперативной памяти.</div>
                                <div class="h3 text-center">
                                    <a href="/ram">Добавьте новую оперативную Память</a> для создания сборки.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одной модели процессора.</div>
                            <div class="h3 text-center"><a href="/cpu">Добавьте новый процесор</a> для создания
                                сборки.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>