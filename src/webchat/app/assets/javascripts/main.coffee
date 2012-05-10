$(document).ready ->
    manager = new MainManager()
    keycodes = new KeyCodes()

    # submit input
    $("#stream_input_field").keyup (e) ->
        if e.keyCode == keycodes.enter
            submit()
    $("#stream_input_send").click (e) ->
        submit()
            
    # function to submit
    submit = () ->
        type = $("#stream_input_options option:selected").val()
        msg = $("#stream_input_field").val()
        msg = msg.slice(0, -1) # remove \n
        $("#stream_input_field").val("")
        manager.send_msg(msg, type)
