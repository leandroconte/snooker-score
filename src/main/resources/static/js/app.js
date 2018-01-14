(function() {

    var API_PLAYER = 'player';
    var API_SCORE = 'score';
    var users;
    var selectedPlayer;

    var $divContent = $(document.createElement('div'))
        .attr('id', 'content')
        .addClass('table-responsive');

    var $divTable = $(document.createElement('table'))
        .addClass('table');
    var $tHead = $(document.createElement('thead'));
    var $tBody = $(document.createElement('tbody'));
    var $trHead = $(document.createElement('tr'));
    var $thName = $(document.createElement('th'))
        .text('Nome');
    var $thPoint = $(document.createElement('th'))
        .text('Pontos');

    $trHead.append($thName);
    $trHead.append($thPoint);
    $tHead.append($trHead);
    $divTable.append($tHead);
    $divTable.append($tBody);
    $divContent.append($divTable);

    $('#users').append($divContent);

    $('#dateScore').datetimepicker({format: 'DD/MM/YYYY'});

    var getUsers = function() {
        $.ajax({
            url: API_PLAYER,
            cache: false
        })
            .done(function (data){
                users = data;
                _buidUsers(users);
            });
    };
    getUsers();

    var _buidUsers = function (users) {

        for (var i = 0; i < users.length; i++) {
            var $trBody = $(document.createElement('tr'))
                .attr('id', 'user-' + users[i].id)
                .addClass('hover-item');

            $trBody.on('click', function () {
                var actualClicked = $(this);
                if (selectedPlayer && actualClicked.attr('id') !== selectedPlayer.attr('id')) {
                    selectedPlayer.removeClass('item-active');
                }
                actualClicked.toggleClass('item-active');
                selectedPlayer = actualClicked;
                _showPointButtons();
            });

            var $tdName = $(document.createElement('td'))
                .addClass('col-sm-2')
                .text(users[i].name);

            var $tdPoint = $(document.createElement('td'))
                .attr('name', 'point')
                .addClass('col-sm-1')
                .text(users[i].scores.length);

            // Append values
            $trBody.append($tdName);
            $trBody.append($tdPoint);
            // Append to table
            $tBody.append($trBody);
        }
        /*_defineWinner(users);*/
    };

    var _showPointButtons = function () {
        var addPointButton = $('#addPointButton');
        if (selectedPlayer && selectedPlayer.hasClass('item-active')) {
            addPointButton.prop('disabled', false);
        } else {
            addPointButton.prop('disabled', true);
        }
    };

    var _defineWinner = function(users) {
        var maxScore = 0;
        var winner = 0;

        for (var i = 0; i < users.length; i++) {
            if (users[i].scores.length > maxScore) {
                maxScore = users[i].scores.length;
                winner = '#user-' + users[i].id;
            }
            $(winner).removeClass('success');
        }
        $(winner).addClass('success');
    };

    $('#addPointsModal').on('click', function () {
        if (selectedPlayer && selectedPlayer.hasClass('item-active')) {
            var selectedPlayerId = selectedPlayer.attr('id').replace('user-', '');
            var columnPoints = $(selectedPlayer.children("td[name='point']")[0]);
            var actualPoint = Number(columnPoints.text());
            var dateInMilli = new Date($('#dateScore').data('DateTimePicker').date()).getTime();
            _addScore(selectedPlayerId, dateInMilli);
            columnPoints.text(actualPoint + 1); //FIXME: reload scores
        }
    });

    var _addScore = function (selectedPlayerId, dateInMilli) {
        $.ajax({
            url: API_SCORE + '/' + selectedPlayerId,
            method: 'POST',
            contentType: 'application/json',
            data : "{ \"date\": " + dateInMilli + " }"
        });
    };

})();