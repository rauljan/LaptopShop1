<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro availabilityAdd>
    <div class="modal fade" id="availabilityAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="availabilityAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="availabilityAddLabel">Добавить новую запись о наличии</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if shopAddresses?has_content>
                    <#if laptopModels?has_content>
                        <div class="modal-body">
                            <form method="post" id="availabilityAddForm" action="/availability/add"
                                  class="form-group needs-validation w-100" novalidate
                                  onsubmit="return validateDatesAdd()">
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Модель ноутбука:</label>
                                        <select class="custom-select" id="laptopSelect" name="laptopModel" required>
                                            <option value="" selected disabled></option>
                                            <#list laptopModels as model>
                                                <option>${model}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не выбран ноутбук</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Цена:</label>
                                        <input type="number" id="priceInput" min="500" max="20000"
                                               class="form-control" name="price" required/>
                                        <div class="invalid-feedback">Введена некорректная цена</div>
                                    </div>
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Количество:</label>
                                        <input type="number" id="quantityInput" min="1" max="2147483646"
                                               class="form-control" name="quantity" required/>
                                        <div class="invalid-feedback">Введено некорректное количество</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Адрес магазина:</label>
                                        <select class="custom-select" id="shopSelect" name="shopAddress" required>
                                            <option value="" selected disabled></option>
                                            <#list shopAddresses as address>
                                                <option>${address}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не выбран адрес магазина</div>
                                    </div>
                                </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                            <@fC.inputsCleaner modalId="availabilityAddModal"
                            inputIds=["priceInput", "quantityInput"]/>
                            <@sC.selectsCleaner modalId="availabilityAddModal"
                            selectIds=["laptopSelect", "shopSelect"]/>
                            <@fSc.fieldsStatusCleaner modalId="availabilityAddModal" formId="availabilityAddForm"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                            <button type="submit" form="availabilityAddForm" class="btn btn-danger">Добавить</button>
                        </div>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                    <div class="h1 text-center">Не найдено ни одного ноутбука.</div>
                                <div class="h3 text-center"><a href="/laptop">Добавьте новый ноутбук</a>
                                    для создания записи о наличии.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одного активного магазина.</div>
                            <div class="h3 text-center"><a href="/shop">Добавьте новый магазин</a>
                                для создания записи о наличии.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>