$(document).ready ->
    manager = new MainManager()
    keycodes = new KeyCodes()

    # channel sidebar navigation
    $(".channels_link").click ->
        if not $("#channel_sidebar #channels").is(":visible")
            $("#channel_sidebar #groups").fadeOut "fast", ->
                $("#channel_sidebar #channels").fadeIn "fast"
        false;
    $(".groups_link").click ->
        if not $("#channel_sidebar #groups").is(":visible")
            $("#channel_sidebar #channels").fadeOut "fast", ->
                $("#channel_sidebar #groups").fadeIn "fast"
        false;                

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
