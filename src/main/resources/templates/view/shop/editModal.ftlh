<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro shopEdit id shopAddress>
    <div class="modal fade" id="shopEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="shopEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="shopEditLabel${id}">Изменить магазин</div>
                    <button type="button" class="close closeHeader" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="shopEditForm${id}" action="/shop/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <label class="d-inline ml-2" for="editAddress">Адрес изменяемого магазина:</label>
                        <input type="text" id="editAddressInput${id}" class="form-control" name="editAddress" required/>
                        <div class="invalid-feedback">Адрес изменяемого магазина задан некорректно</div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editAddressInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editAddressInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="shopEditModal${id}" formId="shopEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#shopEditModal${id}').on('show.bs.modal', function () {
                            $("#editAddressInput${id}").val("${shopAddress}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark closeFooter" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="shopEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>