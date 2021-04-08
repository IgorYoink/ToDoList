$(function(){

    const appendTask = function(data){
        var taskCode = '<a href="#" class="task-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#task-list')
            .append('<div>' + taskCode + '</div>');
    };

    //Loading books on load page
//    $.get('/books/', function(response)
//    {
//        for(i in response) {
//            appendBook(response[i]);
//        }
//    });

    //Getting task
    $(document).on('click', '.task-link', function() {
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
                method: "GET",
                url: '/tasks/' + taskId,
                success: function(response)
                {
                    var complited = (response.complited) ? "выполнена" : "не выполнена";
                    var code = '<div id="description" data-id="' + taskId + '"><h3>' + response.name + '</h3><p>Здесь можно реализовать добавление описания задачи</p>'+
                    '<p>Задача '+ complited +'</p></div>';
                    link.parent().append(code);
                }
        });
        return false;
    });

    //Show adding task form
    $('#show-add-form').click(function(){
        $('#task-form').css('display', 'flex');
    });

    //Closing adding task form
    $('#task-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });


        //Delete task
    $(document).on('click', '.task-delete', function(){
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "DELETE",
            url: '/tasks/' + taskId,
            success: function(response)
            {

                alert('Задача удалена из БД');
                location.reload();
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Задача не найдена!');
                }
            }
        });
        return false;
    });

    //Remove all tasks
    $(document).on('click', '#remove-all-tasks', function(){
        $.ajax({
            method: "DELETE",
            url: '/tasks',
            success: function(response)
            {
                alert('Все задачи удалены из БД!');
                location.reload();
            }

        });
    });

    //Adding task
    $('#save-task').click(function()
    {
        var data = $('#task-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response)
            {
                $('#task-form').css('display', 'none');
                var task = {};
                task.id = response;
                var dataArray = $('#task-form form').serializeArray();
                for(i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
                location.reload();

            }
        });
        return false;
    });

    //Show edit task form
    $(document).on('click', '.task-edit', function() {
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
                method: "GET",
                url: '/tasks/' + taskId,
                success: function(response)
                {
                    var checked = (response.complited) ? "checked" : "";
                    var code = '<div id="edit-task-form" data-id="' + taskId + '"><form><h2>Редактирование задачи</h2>'+
                    '<label>Назавание:</label><input type="text" name="name" value="' + response.name + '"><hr>' +
                    '<label>Выполнено:</label><input type="checkbox" name="complited" id="checkbox" data-id="' + taskId + '" ' + checked + '><br>'+
                    '<button id="save-task-edit">Сохранить</button></form></div>'
                    $('#checkbox').prop('checked', response.complited);
                    link.parent().append(code);
                }
        });
        return false;
    });

    //Edit task
    $(document).on('click', 'button#save-task-edit', function()
    {
        var link = $('#edit-task-form');
        var taskId = link.data('id');
        console.log(link);
        var data = $('#edit-task-form form').serialize();
        data = data + '&isComplited=' + (document.querySelector('input[type="checkbox"][data-id="' + taskId +'"]').checked);
        $.ajax({
            method : "PATCH",
            url: '/tasks/' + taskId,
            data: data,
            success: function(response)
            {
                var task = {};
                task.name = response.name;
                        console.log(response);
                        console.log(data);
                        console.log(response.name);

                location.reload();
            }
        });
        return false;
    });

});