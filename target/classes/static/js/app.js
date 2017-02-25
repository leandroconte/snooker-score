(function() {


    var $divContent = $(document.createElement("div"))
        .attr("id", "content")
        .addClass("table-responsive");

    var $divTable = $(document.createElement("table"))
        .addClass("table");
    var $tHead = $(document.createElement("thead"));
    var $tBody = $(document.createElement("tbody"));
    var $trHead = $(document.createElement("tr"));
    var $thName = $(document.createElement("th"))
        .text("Nome");
    var $thPoint = $(document.createElement("th"))
        .text("Pontos");

    $trHead.append($thName);
    $trHead.append($thPoint);
    $tHead.append($trHead);
    $divTable.append($tHead);
    $divTable.append($tBody);
    $divContent.append($divTable);

    $("#users").append($divContent);

    var getUsers = function() {
        $.ajax({
            url: "user",
            cache: false
        })
        .done(function (data){
            _buidUsers(data);
        });
    };

    getUsers();

    var _buidUsers = function (users) {

        for (var i = 0; i < users.length; i++) {
            var $trBody = $(document.createElement("tr"))
                .attr("id", "user-" + users[i].id);

            var $tdName = $(document.createElement("td"))
                .addClass("col-sm-2")
                .text(users[i].name);

            var $tdPoint = $(document.createElement("td"))
                .attr("name", "point")
                .addClass("col-sm-1")
                .text(users[i].score.points);

            var $tdButtons = $(document.createElement("td"))
                .addClass("col-sm-1 op-user");

            // buttons for points
            var $plus = $(document.createElement("button"))
                .addClass("btn btn-primary btn-point")
                .attr("type", "submit")
                .text("+");

            var $minus = $(document.createElement("button"))
                .addClass("btn btn-danger btn-point")
                .attr("type", "submit")
                .text("-");

            $plus.on("click", function() {
                return _points($(this), true);
            });
            $minus.on("click", function() {
                return _points($(this), false);
            });

            // Append buttons
            $tdButtons.append($minus);
            $tdButtons.append($plus);

            // Append values
            $trBody.append($tdName);
            $trBody.append($tdPoint);
            $trBody.append($tdButtons);
            // Append to table
            $tBody.append($trBody);
        }
        _defineWinner(users);
    }

    var _defineWinner = function(users) {
        var maxScore = 0;
        var winner = 0;

        for (var i = 0; i < users.length; i++) {
            if (users[i].score.points > maxScore) {
                maxScore = users[i].score.points;
                winner = "#user-" + users[i].id;
            }
            $(winner).removeClass("success");
        }
        $(winner).addClass("success");
    };

    var _points = function (element, plus) {
        var $trUser = element.closest("tr");
        var userId = $trUser.attr("id").replace("user-", ""),
            atualPoints = $($trUser.children("td[name='point']")[0]).text();
            urlPath = "";

        if (plus) {
            urlPath = "/plus";
            atualPoints++;
        } else {
            urlPath = "/minus";
            atualPoints--;
        }

        $.ajax({
            url: "user/" + userId + urlPath,
            method: "PATCH"
        })
        .done(function() {
            $($trUser.children("td[name='point']")[0]).text(atualPoints);
        });

    };

})();