<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro laptopAdd>
    <div class="modal fade" id="laptopAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="laptopAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="laptopAddLabel">Добавить новый ноутбук</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if hardwareAssemblyNames?has_content>
                    <#if typeNames?has_content>
                        <#if labelModels?has_content>
                            <div class="modal-body">
                                <form method="post" id="laptopAddForm" action="/laptop/add"
                                      class="form-group needs-validation w-100" novalidate>
                                    <div class="form-row mb-2">
                                        <div class="col">
                                            <label class="d-inline ml-2" for="laptopModel">Название модели:</label>
                                            <select class="custom-select" id="labelSelect" name="labelModel" required>
                                                <option value="" selected disabled></option>
                                                <#list labelModels as labelModel>
                                                    <option>${labelModel}</option>
                                                </#list>
                                            </select>
                                            <div class="invalid-feedback">Не выбрано название модели</div>
                                        </div>
                                    </div>
                                    <div class="form-row my-2">
                                        <div class="col">
                                            <label class="d-inline ml-2" for="typeName">Название типа:</label>
                                            <select class="custom-select" id="typeSelect" name="typeName" required>
                                                <option value="" selected disabled></option>
                                                <#list typeNames as typeName>
                                                    <option>${typeName}</option>
                                                </#list>
                                            </select>
                                            <div class="invalid-feedback">Не выбран тип</div>
                                        </div>
                                    </div>
                                    <div class="form-row my-2">
                                        <div class="col">
                                            <label class="d-inline ml-2" for="hardwareAssemblyName">Название
                                                сборки:</label>
                                            <select class="custom-select" id="hardwareSelect"
                                                    name="hardwareAssemblyName" required>
                                                <option value="" selected disabled></option>
                                                <#list hardwareAssemblyNames as hardwareAssemblyName>
                                                    <option>${hardwareAssemblyName}</option>
                                                </#list>
                                            </select>
                                            <div class="invalid-feedback">Не выбрана сборка</div>
                                        </div>
                                    </div>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                </form>
                                <@sC.selectsCleaner modalId="laptopAddModal"
                                selectIds=["labelSelect", "typeSelect", "hardwareSelect"]/>
                                <@fSc.fieldsStatusCleaner modalId="laptopAddModal" formId="laptopAddForm"/>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                                <button type="submit" form="laptopAddForm" class="btn btn-danger">Добавить</button>
                            </div>
                        <#else>
                            <div class="modal-body">
                                <div class="alert alert-warning" role="alert">
                                    <div class="h1 text-center">Не найдено ни одного наименования.</div>
                                    <div class="h3 text-center">
                                        <a href="/label/add">Добавьте новое наименование</a> для создания ноутбука.
                                    </div>
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одного типа.</div>
                            <div class="h3 text-center">
                                <a href="/type">Добавьте новый тип</a> для создания ноутбука.
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одной сборки.</div>
                            <div class="h3 text-center">
                                <a href="/hardware/add">Добавьте новую сборку</a> для создания ноутбука.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>