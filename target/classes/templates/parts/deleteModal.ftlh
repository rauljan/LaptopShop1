<#macro delete id deletionLink>
    <div class="modal fade" id="deleteModal${id}" tabindex="-1"
         role="dialog" aria-labelledby="typeDeleteLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <div class="col">
                            <img src="https://i.ibb.co/rd7bMFp/red-Trash-Can.png"
                                 class="rounded mx-auto d-block" alt="trashBin">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="h3 text-center">Вы уверены, что хотите удалить запись?</div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="h5 text-center">В дальнейшем его нельзя будет восстановить.</div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <a class="btn btn-danger" href="${deletionLink}" role="button">Удалить</a>
                </div>
            </div>
        </div>
    </div>
</#macro>