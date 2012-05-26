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

    # popup wrapper
    $(".popup_wrapper").click ->
        $(".popup_wrapper").fadeOut "fast"
        $(".popup_window").fadeOut "fast"

    # new channel window
    $("#streams a.change_topic").click ->
        alert "hi"
        $(".popup_wrapper").fadeIn "fast"
        $("#change_topic_window").fadeIn "fast"
        topic = $(@).siblings(".topic").val()
        $("#change_topic_window #change_topic").val(topic)
    $("#change_topic_buttons #change_topic_cancel").click ->
        $(".popup_wrapper").fadeOut "fast"
        $("#change_topic_window").fadeOut "fast"
    $("#change_topic_window #change_topic_buttons #change_topic_change").click ->
        topic = $("#change_topic_window #change_topic").val()
        manager.change_topic(topic)
        $(".popup_wrapper").fadeOut "fast"
        $("#change_topic_window").fadeOut "fast"     


    # new channel window
    $("#channel_sidebar #channels .utils .newchannel").click ->
        $(".popup_wrapper").fadeIn "fast"
        $("#newchannel_window").fadeIn "fast"
        $("#newchannel_window #newchannel_name").val("")
        $("#newchannel_window #newchannel_topic").val("")
        $("#newchannel_window #newchannel_public").prop("checked", false)
    $("#newchannel_buttons #newchannel_cancel").click ->
        $(".popup_wrapper").fadeOut "fast"
        $("#newchannel_window").fadeOut "fast"
    $("#newchannel_window #newchannel_buttons #newchannel_create").click ->
        name = $("#newchannel_window #newchannel_name").val()
        topic = $("#newchannel_window #newchannel_topic").val()
        is_public = $("#newchannel_window #newchannel_public").is(":checked")
        manager.create_channel(name, topic, is_public)
        $(".popup_wrapper").fadeOut "fast"
        $("#newchannel_window").fadeOut "fast"        

    # invite window
    $("#info_sidebar #channel_info .utils .invite").click ->
        $(".popup_wrapper").fadeIn "fast"
        $("#invite_window").fadeIn "fast"
        $("#invite_filter").focus()
        manager.reset_invite_selection()
        # reset invite filter
        $("#invite_window #invite_filter").val("")
        $("#invite_window #selected_preview .select_list ul li").each ->
            $(@).show()
    $("#invite_buttons #invite_cancel").click ->
        $(".popup_wrapper").fadeOut "fast"
        $("#invite_window").fadeOut "fast"
    $("#invite_buttons #invite_ok").click ->
        $(".popup_wrapper").fadeOut "fast"
        $("#invite_window").fadeOut "fast"
        manager.invite_selection()
    $("#invite_window #invite_filter").keyup ->
        text = $(@).val()
        console.log text
        $("#invite_window #selected_preview .select_list ul li").each ->
            if $(@).html().toLowerCase().indexOf(text) != -1
                $(@).show()
            else
                $(@).hide()
        
