$(document).ready ->

    # set focus on input element on page load
    $("#input_field").focus()

    KeyCodes =
        enter: 13
        tab: 9
        escape: 27

    $("#input_field").keydown (e) ->
        if e.keyCode == KeyCodes.tab
            return false
        if e.keyCode == KeyCodes.enter and not e.shiftKey
            $("#input_send").trigger('click')
            return false