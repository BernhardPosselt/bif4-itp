$(document).ready ->
    manager = new MainManager()
    keycodes = new KeyCodes()

    # left channel sidebar navigation
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
        if e.keyCode == keycodes.tab
            $(@).val(manager.complete_name($(@).val()))
        if e.keyCode == keycodes.enter and not e.shiftKey
            console.log keycodes.shift_pressed
            submit()
    # dont jump on hitting enter on an empty input field
    $("#input_field").keydown (e) ->
        if e.keyCode == keycodes.tab
            return false
        if e.keyCode == keycodes.enter
            if $(@).val() == ""
                return false
    $("#input_send").click (e) ->
        submit()
            
    # function to submit
    submit = () ->
        type = $("#input_options option:selected").val()
        msg = $("#input_field").val()
        msg = msg.slice(0, -1) # remove \n
        $("#input_field").val("")
        # only send when there is min 1 char
        if msg.length > 0
            manager.send_msg(msg, type)
        
    # right sidebar util links
    $("#info_sidebar #channel_info .utils .invite").click ->
        $("#add_wrapper").fadeIn "fast"
        $("#add_form").fadeIn "fast"
    $("#add_buttons #add_cancel").click ->
        $("#add_wrapper").fadeOut "fast"
        $("#add_form").fadeOut "fast"
    $("#add_wrapper").click ->
        $("#add_wrapper").fadeOut "fast"
        $("#add_form").fadeOut "fast"
