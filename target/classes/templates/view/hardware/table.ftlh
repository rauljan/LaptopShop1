<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@htmlPad.page "Hardware">
    <div class="row">
        <div class="col">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                        data-target="#hardwareAddModal">
                    <i class="far fa-plus-square"></i> Добавить новую сборку
                </button>
                <@addModal.hardwareAdd/>
            </@security.authorize>
        </div>
        <div class="d-flex justify-content-center">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                        data-target="#HardwareExcelUploadModal">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
                </button>
                <@eU.excelUpload tableNameEn="Hardware" tableNameUk="сборок"/>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a class="btn btn-success fixedBtnHeight" href="/hardware/exportExcel" role="button">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
                </a>
            </@security.authorize>
        </div>
        <div class="col-lg-6 col-sm-7 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#hardwareSearch" aria-expanded="false" aria-controls="hardwareSearch">
                Найти сборку
            </button>
            <div class="collapse form-group" id="hardwareSearch">
                <form method="get" action="/hardware">
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="assemblyName"
                                   value="${RequestParameters.assemblyName!}" placeholder="Название"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="displayModel"
                                   value="${RequestParameters.displayModel!}" placeholder="Модель дисплея"/>
                        </div>
                        <div class="col">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2"
                                   name="displayDiagonal"
                                   value="${RequestParameters.displayDiagonal!}" placeholder="Диагональ дисплея"/>
                        </div>
                        <div class="col-3">
                            <input type="text" class="form-control mr-1 mt-2" name="displayType"
                                   value="${RequestParameters.displayType!}" placeholder="Тип дисплея"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="cpuModel"
                                   value="${RequestParameters.cpuModel!}" placeholder="Модель процессора"/>
                        </div>
                        <div class="col">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="cpuFrequency"
                                   value="${RequestParameters.cpuFrequency!}" placeholder="Частота процессора"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="gpuModel"
                                   value="${RequestParameters.gpuModel!}" placeholder="Модель видеокарты"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="gpuMemory"
                                   value="${RequestParameters.gpuMemory!}" placeholder="Объем видеопамяти"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="ramModel"
                                   value="${RequestParameters.ramModel!}" placeholder="Модель оперативной памяти"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="ramMemory"
                                   value="${RequestParameters.ramMemory!}" placeholder="Объем опер. памяти"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="ssdModel"
                                   value="${RequestParameters.ssdModel!}" placeholder="Модель SSD диска"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="ssdMemory"
                                   value="${RequestParameters.ssdMemory!}" placeholder="Объем SSD диска"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="hddModel"
                                   value="${RequestParameters.hddModel!}" placeholder="Модель HDD диска"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="hddMemory"
                                   value="${RequestParameters.hddMemory!}" placeholder="Объем HDD диск"/>
                        </div>
                        <div class="col-4">
                            <input type="number" min="1" class="form-control" name="price"
                                   value="${RequestParameters.price!}" placeholder="Цена"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>

    <div class="mt-4">
        <div class="row">
            <div class="col">
                <@eO.errorOutput errorMessage!/>
            </div>
        </div>
        <#if hardware?has_content>
            <div class="h1">Список сборок:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th class="text-danger" scope="col">Номер на складе</th>
                    </@security.authorize>

                    <th scope="col">Название сборки</th>
                    <th scope="col">Модель процесора</th>
                    <th scope="col">Модель видеокарты</th>
                    <th scope="col">Модель дисплея</th>
                    <th scope="col">Модель оперативной памяти</th>
                    <th scope="col">Модель SSD диска</th>
                    <th scope="col">Модель HDD диска</th>
                    <th scope="col">Цена</th>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th scope="col">Изменить</th>
                        <th scope="col">Удалить</th>
                    </@security.authorize>
                    <@security.authorize access="hasAnyAuthority('USER')">
                        <th scope="col">Корзина</th>
                    </@security.authorize>

                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th></th>
                        <th></th>
                    </@security.authorize>
                </tr>
                </thead>
                <tbody>
                <#list hardware as assembly>
                    <tr>
                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <th class="text-danger" scope="row">${assembly.id}</th>
                        </@security.authorize>

                        <td>${assembly.assemblyName}</td>
                        <td><#if assembly.cpu??>${assembly.cpu.model}<#else>Отсутствует</#if></td>
                        <td><#if assembly.gpu??>${assembly.gpu.model}<#else>Отсутствует</#if></td>
                        <td><#if assembly.display??>${assembly.display.model}<#else>Отсутствует</#if></td>
                        <td><#if assembly.ram??>${assembly.ram.model}<#else>Отсутствует</#if></td>
                        <td><#if assembly.ssd??>${assembly.ssd.model}<#else>Отсутствует</#if></td>
                        <td><#if assembly.hdd??>${assembly.hdd.model}<#else>Отсутствует</#if></td>
                        <td>${assembly.price}</td>
                        <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
                            <td><button class="btn btn-outline-dark">Добавить в корзину</button></td>
                        </@security.authorize>

                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <td>
                                <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                        data-target="#hardwareEditModal${assembly.id?c}">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <@editModal.hardwareEdit id = "${assembly.id?c}"
                                currentAssemblyName = "${assembly.assemblyName}"
                                currentCpuModel = "${(assembly.cpu??)?then(assembly.cpu.model, '')}"
                                currentGpuModel = "${(assembly.gpu??)?then(assembly.gpu.model, '')}"
                                currentDisplayModel = "${(assembly.display??)?then(assembly.display.model, '')}"
                                currentRamModel = "${(assembly.ram??)?then(assembly.ram.model, '')}"
                                currentSsdModel = "${(assembly.ssd??)?then(assembly.ssd.model, '')}"
                                currentHddModel = "${(assembly.hdd??)?then(assembly.hdd.model, '')}"
                                currentPrice="${assembly.price}"/>
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                        data-target="#deleteModal${assembly.id?c}">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <@deleteModal.delete id="${assembly.id?c}"
                                deletionLink="/hardware/delete/${assembly.id?c}"/>
                            </td>
                        </@security.authorize>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одной   сборки.</div>
        </#if>
    </div>
</@htmlPad.page>