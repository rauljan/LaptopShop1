<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro cpuAdd>
    <div class="modal fade" id="cpuAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="cpuAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="cpuAddLabel">Добавить новый процесор</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="cpuAddForm" action="/cpu/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newCPU" onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Название новой модели:</label>
                                <input type="text" id="newModelInput" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Название новой модели задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="frequency">Тактовая частота (GhZ):</label>
                                <input type="number" id="newFrequencyInput" step="0.1" min="0.1" max="10"
                                       class="form-control" name="frequency" required/>
                                <div class="invalid-feedback">Тактовая частота задана некорректно</div>
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
                    <@eB.emptyBreaker inputIds=["newModelInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newModelInput"]/>
                    <@fC.inputsCleaner modalId="cpuAddModal" inputIds=["newModelInput", "newFrequencyInput", "newPriceInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="cpuAddModal" formId="cpuAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="cpuAddForm" class="btn btn-danger">Добавить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>