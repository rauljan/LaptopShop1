<link rel="stylesheet" href="/styles/navbarStyle.css">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<nav class="navbar navbar-expand-xl bg-light">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <li class="nav-item text-center">
                    <a class="nav-link" href="/user">User</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/availability">Availability</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/basket">Basket</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/buying">Buying</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/client">Client</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/employee">Employee</a>
                </li>
            </@security.authorize>
            <li class="nav-item text-center">
                <a class="nav-link" href="/shop">Shop</a>
            </li>
            <@security.authorize access="isAuthenticated()">
                <li class="nav-item text-center">
                    <a class="nav-link" href="/cpu">CPU</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/gpu">GPU</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/ram">RAM</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/hdd">HDD</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/ssd">SSD</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/display">Display</a>
                </li>
            </@security.authorize>
            <li class="nav-item text-center">
                <a class="nav-link" href="/hardware">Hardware</a>
            </li>
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <li class="nav-item text-center">
                    <a class="nav-link" href="/type">Type</a>
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link" href="/label">Label</a>
                </li>
            </@security.authorize>
            <li class="nav-item text-center">
                <a class="nav-link" href="/laptop">Laptop</a>
            </li>
        </ul>
        <@security.authorize access="!isAuthenticated()">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <a class="btn btn-outline-dark" href="/login" role="button"><i class="fas fa-user-cog"></i>
                        Войти</a>
                </div>
            </div>
        </@security.authorize>
        <@security.authorize access="isAuthenticated()">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <form action="/userBasket">
                    <div class="col d-flex justify-content-center">
                        <a class="btn btn-outline-dark" href="/userBasket" role="button"><i class="fas fa-user-cog"></i>
                            Корзина</a>
                    </div>
                    </form>
                    <form action="/logout" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-outline-dark"><i class="fas fa-user-cog"></i> Выйти
                        </button>
                    </form>
                </div>
            </div>
        </@security.authorize>
    </div>
</nav>