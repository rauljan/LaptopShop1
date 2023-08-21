<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro availabilityEdit id currentLaptopModel currentPrice currentQuantity currentShopAddress>
    <div class="modal fade" id="availabilityEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="availabilityEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="availabilityEditLabel${id}">Изменить запись о наличии</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/availability/edit/${id}" id="availabilityEditForm${id}"
                          class="form-group needs-validation w-100"
                          novalidate onsubmit="return validateDates${id}()">
                        <#if laptopModels?has_content>
                            <div class="form-row mb-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="laptopModelSelect${id}">Модель ноутбука:</label>
                                    <select class="custom-select" id="laptopModelSelect${id}" name="editLaptopModel"
                                            required>
                                        <#list laptopModels as model>
                                            <option value="${model}">${model}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбран ноутбук</div>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="priceInput${id}">Цена:</label>
                                <input type="number" min="500" max="15000"
                                       id="priceInput${id}" class="form-control" name="editPrice"
                                       value="${currentPrice}" required/>
                                <div class="invalid-feedback">Введена некорректная цена</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="quantityInput${id}">Количество:</label>
                                <input type="number" min="1" max="2147483646"
                                       id="quantityInput${id}" class="form-control" name="editQuantity"
                                       value="${currentQuantity}" required/>
                                <div class="invalid-feedback">Введена некорректное количество</div>
                            </div>
                        </div>

                        <#if shopAddresses?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="shopSelect${id}">Адрес магазина:</label>
                                    <select class="custom-select" id="shopSelect${id}" name="editShopAddress" required>
                                        <#list shopAddresses as address>
                                            <option value="${address}">${address}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбран адрес магазина</div>
                                </div>
                            </div>
                        </#if>

                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@fSc.fieldsStatusCleaner modalId="availabilityEditModal${id}" formId="availabilityEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#availabilityEditModal${id}").on("show.bs.modal", function () {
                            $("#laptopModelSelect${id}").val("${currentLaptopModel}").change();
                            $("#priceInput${id}").val("${currentPrice}").change();
                            $("#quantityInput${id}").val("${currentQuantity}").change();
                            $("#quantityInput${id}").val("${currentQuantity}").change();
                            $("#shopSelect${id}").val("${currentShopAddress}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="availabilityEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>