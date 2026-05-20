const menus = [

    {
        name: "Dashboard",
        link: "/dashboard"
    },

    {
        name: "Quản lý chi tiêu",
        link: "/expenses"
    },

    {
        name: "Thu nhập",
        link: "/incomes"
    },

    {
        name: "Ngân sách",
        link: "/budgets"
    },

    {
        name: "Danh mục",
        link: "/categories"
    },

    {
        name: "Thống kê",
        link: "/statistics"
    },

    {
        name: "Tài khoản",
        link: "/account"
    }

];

window.addEventListener("load", function () {

    const input =
            document.getElementById("menuSearch");

    const resultBox =
            document.getElementById("searchResult");

    if (!input || !resultBox) return;

    input.addEventListener("keyup", function () {

        let keyword =
                this.value.toLowerCase().trim();

        resultBox.innerHTML = "";

        if (keyword === "") {

            resultBox.style.display = "none";

            return;
        }

        let filtered =
                menus.filter(menu =>
                        menu.name.toLowerCase()
                                .includes(keyword)
                );

        if (filtered.length > 0) {

            resultBox.style.display = "block";

            filtered.forEach(menu => {

                resultBox.innerHTML += `

                    <a href="${menu.link}"
                       class="list-group-item list-group-item-action">

                        ${menu.name}

                    </a>

                `;
            });

        } else {

            resultBox.innerHTML = `

                <div class="list-group-item text-muted">

                    Không tìm thấy menu

                </div>

            `;

            resultBox.style.display = "block";
        }

    });

});