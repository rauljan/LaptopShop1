<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro displayEdit id displayModel displayType displayDiagonal displayResolution displayPrice>
    <div class="modal fade" id="displayEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="displayEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="displayEditLabel${id}">Изменить информацию о дисплей</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="displayEditForm${id}" action="/display/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Название изменяемой модели:</label>
                                <input type="text" id="editModelInput${id}" class="form-control" name="editModel"
                                       required/>
                                <div class="invalid-feedback">Название изменяемой модели задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="type">Название типа:</label>
                                <input type="text" id="editTypeInput${id}" class="form-control" name="editType"
                                       required/>
                                <div class="invalid-feedback">Название типа задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="diagonal">Диагональ дисплея:</label>
                                <input type="number" id="editDiagonalInput${id}" class="form-control"
                                       step="0.1" min="0.1" max="40" name="editDiagonal" required/>
                                <div class="invalid-feedback">Диагональ задана некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="resolution">Разрешение дисплея:</label>
                                <input type="text" id="editResolutionInput${id}" class="form-control" name="editResolution"
                                       required/>
                                <div class="invalid-feedback">Разрешение задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row mn-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="price">Цена:</label>
                                <input type="number" id="editPriceInput${id}" min="1" max="15000" class="form-control"
                                       name="editPrice" required/>
                                <div class="invalid-feedback">Некорректная цена</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editModelInput${id}", "editTypeInput${id}", "editResolutionInput${id}"]/>
                    <@vC.valueBreaker id="${id}"
                    inputIds=["editModelInput${id}", "editTypeInput${id}", "editResolutionInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="displayEditModal${id}" formId="displayEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#displayEditModal${id}').on('show.bs.modal', function () {
                            $("#editModelInput${id}").val("${displayModel}").change();
                            $("#editTypeInput${id}").val("${displayType}").change();
                            $("#editDiagonalInput${id}").val("${displayDiagonal}").change();
                            $("#editResolutionInput${id}").val("${displayResolution}").change();
                            $("#editPriceInput${id}").val("${displayPrice}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="displayEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>