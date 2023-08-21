<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro displayAdd>
    <div class="modal fade" id="displayAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="displayAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="displayAddLabel">Добавить новый дисплей</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="displayAddForm" action="/display/add"
                          class="form-group needs-validation w-100" novalidate modelAttribute="newDisplay"
                          onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Название новой модели:</label>
                                <input type="text" id="newModelInput" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Название новой модели задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="type">Название типа:</label>
                                <input type="text" id="newTypeInput" class="form-control" name="type" required/>
                                <div class="invalid-feedback">Название типа задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="diagonal">Диагональ дисплея:</label>
                                <input type="number" id="newDiagonalInput" class="form-control"
                                       step="0.1" min="0.1" max="40" name="diagonal" required/>
                                <div class="invalid-feedback">Диагональ задана некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="resolution">Разрешение дисплея:</label>
                                <input type="text" id="newResolutionInput" class="form-control" name="resolution"
                                       required/>
                                <div class="invalid-feedback">Разрешение задано некорректно</div>
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
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["newModelInput", "newTypeInput", "newResolutionInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newModelInput", "newTypeInput", "newResolutionInput"]/>
                    <@fC.inputsCleaner modalId="displayAddModal"
                    inputIds=["newModelInput", "newTypeInput", "newResolutionInput", "newDiagonalInput", "newPriceInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="displayAddModal" formId="displayAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="displayAddForm" class="btn btn-danger">Добавить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>