<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro buyingAdd>
    <div class="modal fade" id="buyingAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="buyingAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="buyingAddLabel">Добавить новую покупку</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if basketIds?has_content>
                    <#if laptopModels?has_content>
                        <div class="modal-body">
                            <form method="post" id="buyingAddForm" action="/buying/add"
                                  class="form-group needs-validation w-100" novalidate>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="basketId">ID корзины:</label>
                                        <select class="custom-select" id="basketSelect" name="basketId" required>
                                            <option value="" selected disabled></option>
                                            <#list basketIds as basketId>
                                                <option>${basketId}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не выбран ID корзины!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Модель ноутбука:</label>
                                        <select class="custom-select" id="laptopSelect" name="laptopModel" required>
                                            <option value="" selected disabled></option>
                                            <#list laptopModels as laptopModel>
                                                <option>${laptopModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не выбрана модель ноутбука!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="totalPrice">Общая цена:</label>
                                        <input type="number" id="priceInput" min="500" max="15000"
                                               class="form-control" name="totalPrice" required/>
                                        <div class="invalid-feedback">Введена некорректная цена</div>
                                    </div>
                                </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                            <@fC.inputsCleaner modalId="buyingAddModal"
                            inputIds=["priceInput"]/>
                            <@sC.selectsCleaner modalId="buyingAddModal"
                            selectIds=["basketSelect", "laptopSelect"]/>
                            <@fSc.fieldsStatusCleaner modalId="buyingAddModal" formId="buyingAddForm"/>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                            <button type="submit" form="buyingAddForm" class="btn btn-danger">Добавить</button>
                        </div>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не найдено ни одного  ноутбука.</div>
                                <div class="h3 text-center">
                                    <a href="/laptop">Добавьте новый ноутбук</a> для создания покупки.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одной  корзины.</div>
                            <div class="h3 text-center">
                                <a href="/basket">Добавьте новую корзину</a> для создания покупки.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>