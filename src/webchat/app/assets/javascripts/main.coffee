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
        
    # set focus on input element on page load
    $("#input_field").focus()

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


    # new channel window
    $("#channel_sidebar #channels .utils .newchannel").click ->
        $("#newchannel_wrapper").fadeIn "fast"
        $("#newchannel_form").fadeIn "fast"
        $("#newchannel_form #newchannel_name").val("")
        $("#newchannel_form #newchannel_topic").val("")
    $("#newchannel_buttons #newchannel_cancel").click ->
        $("#newchannel_wrapper").fadeOut "fast"
        $("#newchannel_form").fadeOut "fast"
    $("#newchannel_wrapper").click ->
        $("#newchannel_wrapper").fadeOut "fast"
        $("#newchannel_form").fadeOut "fast"
    $("#newchannel_form #newchannel_buttons #newchannel_create").click ->
        name = $("#newchannel_form #newchannel_name").val()
        topic = $("#newchannel_form #newchannel_topic").val()
        manager.create_channel(name, topic)
        $("#newchannel_wrapper").fadeOut "fast"
        $("#newchannel_form").fadeOut "fast"        

    # invite window
    $("#info_sidebar #channel_info .utils .invite").click ->
        $("#add_wrapper").fadeIn "fast"
        $("#add_form").fadeIn "fast"
        $("#add_search").focus()
        manager.reset_invite_selection()
        # reset invite filter
        $("#add_form #add_search").val("")
        $("#add_form #selected_preview .select_list ul li").each ->
            $(@).show()
    $("#add_buttons #add_cancel").click ->
        $("#add_wrapper").fadeOut "fast"
        $("#add_form").fadeOut "fast"
    $("#add_buttons #add").click ->
        $("#add_wrapper").fadeOut "fast"
        $("#add_form").fadeOut "fast"
        manager.invite_selection()
    $("#add_wrapper").click ->
        $("#add_wrapper").fadeOut "fast"
        $("#add_form").fadeOut "fast"
    $("#add_form #add_search").keyup ->
        text = $(@).val()
        console.log text
        $("#add_form #selected_preview .select_list ul li").each ->
            if $(@).html().toLowerCase().indexOf(text) != -1
                $(@).show()
            else
                $(@).hide()
        
    # file upload window
    $("#info_sidebar #file_info .utils .upload").click ->
        $("#upload_wrapper").fadeIn "fast"
        $("#upload_form").fadeIn "fast"
    $("#upload_buttons #upload_cancel").click ->
        $("#upload_wrapper").fadeOut "fast"
        $("#upload_form").fadeOut "fast"
    $("#upload_wrapper").click ->
        $("#upload_wrapper").fadeOut "fast"
        $("#upload_form").fadeOut "fast"
