<#import "../parts/htmlPad.ftlh" as pad>
<#import "../graph/pieGraphScript.ftlh" as pieGraphScript>

<@pad.page "Basket graphs">
    <div class="accordion" id="basketAccordion">
        <div class="card">
            <div class="card-header" id="employeeHeading">
                <h2 class="mb-0 text-center">
                    <button class="btn btn-danger btn-lg btn-block collapsed" type="button" data-toggle="collapse"
                            data-target="#employeeGraph" aria-expanded="true" aria-controls="employeeGraph">
                        Статистический график продаж по работникам
                    </button>
                </h2>
            </div>
            <div id="employeeGraph" class="collapse" aria-labelledby="employeeHeading" data-parent="#basketAccordion">
                <div id="employeeGraphContainer" class="w-100" style="height: 650px"></div>
            </div>
        </div>

        <div class="card">
            <div class="card-header" id="clientHeading">
                <h2 class="mb-0">
                    <button class="btn btn-dark btn-lg btn-block collapsed" type="button" data-toggle="collapse"
                            data-target="#clientGraph" aria-expanded="false" aria-controls="clientGraph">
                        Статистический график продаж по клиентам
                    </button>
                </h2>
            </div>
            <div id="clientGraph" class="collapse" aria-labelledby="clientHeading" data-parent="#basketAccordion">
                <div id="clientGraphContainer" class="w-100" style="height: 650px"></div>
            </div>
        </div>
    </div>

    <@pieGraphScript.pieGraph
        collapseId="employeeGraph" graphContainer="employeeGraphContainer"
        titleText="Процент продаж по работнику" units="продажи" dataPoints=employeesDataPoints>
    </@pieGraphScript.pieGraph>

    <@pieGraphScript.pieGraph
    collapseId="clientGraph" graphContainer="clientGraphContainer"
    titleText="Процент покупок по клиенту" units="покупки" dataPoints=clientsDataPoints>
    </@pieGraphScript.pieGraph>
</@pad.page>