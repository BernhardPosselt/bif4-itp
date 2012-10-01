WebChat = window.WebChat

$(document).ready ->
    # left channel sidebar navigation
    $(".channels_link").click ->
        if not $("#channel_sidebar #channels").is(":visible")
            $("#channel_sidebar #groups").hide()
            $("#channel_sidebar #channels").show()
        false;
    $(".groups_link").click ->
        if not $("#channel_sidebar #groups").is(":visible")
            $("#channel_sidebar #channels").hide()
            $("#channel_sidebar #groups").show()
        false;                
        
    # set focus on input element on page load
    $("#input_field").focus()