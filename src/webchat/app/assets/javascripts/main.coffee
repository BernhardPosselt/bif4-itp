$(document).ready ->
    manager = new MainManager()
    keycodes = new KeyCodes()
    manager.channels.init({"1": {"name": "abc"}})
    manager.channels.init_ui()

    # submit input
    $("#input_field").keyup (e) ->
        if e.keyCode == keycodes.enter
            submit()
    $("#input_send").click (e) ->
        submit()
            
    # function to submit
    submit = () ->
        type = $("#input_options option:selected").val()
        msg = $("#input_field").val()
        msg = msg.slice(0, -1) # remove \n
        $("#input_field").val("")
        manager.send_msg(msg, type)
