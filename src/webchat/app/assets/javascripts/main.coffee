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
        # autocomplete on tab
        if e.keyCode == keycodes.tab
            $(@).val(manager.complete_name($(@).val()))
        # enter without shift will submit
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
    
    # hide popup windows on escape, commit on enter
    $("body").keyup (e) ->
        if e.keyCode == keycodes.escape
            $(".popup_window:visible").fadeOut "fast"
            $(".popup_wrapper:visible").fadeOut "fast"
        if e.keyCode == keycodes.enter
            $(".popup_window:visible .submit").each ->
                if $(@).attr("disabled") != "disabled"
                    $(@).trigger "click"
            
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
        $(".popup_wrapper:visible").fadeOut "fast"
        $(".popup_window:visible").fadeOut "fast"
    $(".popup_window .cancel").click ->
        $(".popup_wrapper:visible").fadeOut "fast"
        $(".popup_window:visible").fadeOut "fast"

    # change topic window
    $("#streams a.change_topic").live "click", ->
        $(".popup_wrapper").fadeIn "fast"
        $("#change_topic_window").fadeIn "fast"
        topic = $(@).parent().parent().siblings(".stream_meta").children(".topic").html()
        $("#change_topic_window #change_topic").html(topic)
    $("#change_topic_window .submit").click ->
        topic = $("#change_topic_window #change_topic").val()
        manager.change_topic(topic)
        $(".popup_wrapper").fadeOut "fast"
        $("#change_topic_window").fadeOut "fast"     

    # change channel name window
    $("#streams a.change_channel_name").live "click", ->
        $(".popup_wrapper").fadeIn "fast"
        $("#change_channel_name_window").fadeIn "fast"
        name = $(@).parent().siblings("span").html()
        $("#change_channel_name_window #change_channel_name").val(name)
    $("#change_channel_name_window .submit").click ->
        name = $("#change_channel_name_window #change_channel_name").val()
        manager.change_channel_name(name)
        $(".popup_wrapper").fadeOut "fast"
        $("#change_channel_name_window").fadeOut "fast"   
      
    # delete channel name window
    $("#streams a.delete_channel").live "click", ->
        $(".popup_wrapper").fadeIn "fast"
        $("#delete_channel_window").fadeIn "fast"
        name = $(@).parent().siblings("span").html()
        $("#delete_channel_window #delete_channel").html(name)
    $("#delete_channel_window .submit").click ->
        manager.delete_channel()
        $(".popup_wrapper").fadeOut "fast"
        $("#delete_channel_window").fadeOut "fast"    

    # close channel name window
    $("#streams a.close_channel").live "click", ->
        $(".popup_wrapper").fadeIn "fast"
        $("#close_channel_window").fadeIn "fast"
        name = $(@).parent().siblings("span").html()
        $("#close_channel_window #close_channel").html(name)
    $("#close_channel_window .submit").click ->
        manager.close_channel()
        $(".popup_wrapper").fadeOut "fast"
        $("#close_channel_window").fadeOut "fast"    

    # edit profile window
    $("#edit_profile_link").click ->
        $(".popup_wrapper").fadeIn "fast"
        $("#edit_profile_window").fadeIn "fast"
    $("#edit_profile_window .submit").click ->
        data = {}
        data.username = $("#edit_profile_window #edit_profile_username").val()
        data.prename = $("#edit_profile_window #edit_profile_first_name").val()
        data.lastname = $("#edit_profile_window #edit_profile_last_name").val()
        data.password = $("#edit_profile_window #edit_profile_password").val()
        data.repeat_password = $("#edit_profile_window #edit_profile_password_repeat").val()
        data.email = $("#edit_profile_window #edit_profile_email").val()
        manager.update_profile(data)
        $(".popup_wrapper").fadeOut "fast"
        $("#edit_profile_window").fadeOut "fast" 

    # check password match
    $("#edit_profile_window #edit_profile_password").keyup ->
        if password_check($("#edit_profile_password"), $("#edit_profile_password_repeat"))
            allow_submit($("#edit_profile_window .submit"), true)
        else
            allow_submit($("#edit_profile_window .submit"), false)
    $("#edit_profile_window #edit_profile_password_repeat").keyup ->
        if password_check($("#edit_profile_password"), $("#edit_profile_password_repeat"))
            allow_submit($("#edit_profile_window .submit"), true)
        else
            allow_submit($("#edit_profile_window .submit"), false)

    # checks the input field for a password and its repeat field and adds or
    # removes the class no_match accordingly
    # input: the input html element with the password
    # repeat_input: the input html for the repeated password
    # return: true if matches, false if not
    password_check = (input, repeat_input) ->
        if input.val() == repeat_input.val()
            input.removeClass("no_match")
            repeat_input.removeClass("no_match")
            return true
        else
            input.removeClass("no_match")
            repeat_input.removeClass("no_match")
            return false
    
    # enables a submit buttons and submit by hitting enter or disables it
    # submit_button: the button html element which submits the data
    # enabled: if true, the button will be enabled, false disables him        
    allow_submit = (submit_button, enabled) ->
        if enabled
            submit_button.removeAttr("disabled")
        else
            submit_button.attr("disabled", "disabled")

    # new channel window
    $("#channel_sidebar #channels .utils .newchannel").click ->
        $(".popup_wrapper").fadeIn "fast"
        $("#newchannel_window").fadeIn "fast"
        $("#newchannel_window #newchannel_name").val("")
        $("#newchannel_window #newchannel_topic").val("")
        $("#newchannel_window #newchannel_public").prop("checked", false)
    $("#newchannel_window .submit").click ->
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
    $("#invite_window .submit").click ->
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

