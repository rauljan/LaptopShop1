<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro buyingEdit id currentBasketId currentLaptopModel currentTotalPrice>
    <div class="modal fade" id="buyingEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="buyingEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="buyingEditLabel${id}">Изменить информацию о покупке</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/buying/edit/${id}" id="buyingEditForm${id}"
                          class="form-group needs-validation w-100" novalidate>
                        <#if basketIds?has_content>
                            <div class="form-row mb-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="basketSelect${id}">ID корзины:</label>
                                    <select class="custom-select" id="basketSelect${id}" name="editBasketId" required>
                                        <#list basketIds as basketId>
                                            <option value="${basketId}">${basketId}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбран ID корзины</div>
                                </div>
                            </div>
                        </#if>

                        <#if laptopModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="laptopSelect${id}">Модель ноутбука:</label>
                                    <select class="custom-select" id="laptopSelect${id}" name="editLaptopModel" required>
                                        <#list laptopModels as laptopModel>
                                            <option value="${laptopModel}">${laptopModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбрана модель ноутбука</div>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="totalPriceInput${id}">Общая цена:</label>
                                <input type="number" min="5000" max="2147483646"
                                       id="totalPriceInput${id}" class="form-control" name="editTotalPrice" required/>
                                <div class="invalid-feedback">Введена некорректная ціну! (<5000 aбо >2147483646)</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@fSc.fieldsStatusCleaner modalId="buyingEditModal${id}" formId="buyingEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#buyingEditModal${id}").on("show.bs.modal", function () {
                            $("#basketSelect${id}").val("${currentBasketId}").change();
                            $("#laptopSelect${id}").val("${currentLaptopModel}").change();
                            $("#totalPriceInput${id}").val("${currentTotalPrice}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="buyingEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>