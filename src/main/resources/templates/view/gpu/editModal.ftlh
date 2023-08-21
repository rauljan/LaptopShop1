<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro gpuEdit id gpuModel gpuMemory gpuPrice>
    <div class="modal fade" id="gpuEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="gpuEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="gpuEditLabel${id}">Изменить информацию о видеокарте</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="gpuEditForm${id}" action="/gpu/edit/${id}"
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
                                <label class="d-inline ml-2" for="memory">Объем памяти (GiB):</label>
                                <input type="number" id="editMemoryInput${id}" min="1" max="225" class="form-control"
                                       name="editMemory" required/>
                                <div class="invalid-feedback">Объем памяти задан некорректно</div>
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
                    <@fSc.fieldsStatusCleaner modalId="gpuEditModal${id}" formId="gpuEditForm${id}"/>

                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#gpuEditModal${id}').on('show.bs.modal', function () {
                            $("#editModelInput${id}").val("${gpuModel}").change();
                            $("#editMemoryInput${id}").val("${gpuMemory}").change();
                            $("#editPriceInput${id}").val("${gpuPrice}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="gpuEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>