<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro cpuEdit id cpuModel cpuFrequency cpuPrice>
    <div class="modal fade" id="cpuEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="cpuEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="cpuEditLabel${id}">Изменить информацию о процессоре</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="cpuEditForm${id}" action="/cpu/edit/${id}"
                          class="form-group needs-validation w-100" novalidate
                          onsubmit="return validateForm${id}()">
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
                                <label class="d-inline ml-2" for="frequency">Тактовая частота (GhZ):</label>
                                <input type="number" id="editFrequencyInput${id}" step="0.1" min="0.1" max="10"
                                       class="form-control" name="editFrequency" required/>
                                <div class="invalid-feedback">Тактовая частота задана некорректно</div>
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
                    <@eB.emptyBreaker inputIds=["editModelInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editModelInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="cpuEditModal${id}" formId="cpuEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#cpuEditModal${id}').on('show.bs.modal', function () {
                            $("#editModelInput${id}").val("${cpuModel}").change();
                            $("#editFrequencyInput${id}").val("${cpuFrequency}").change();
                            $("#editPriceInput${id}").val("${cpuPrice}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="cpuEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>