app = angular.module('WebChat', []).
    config ($provide) ->
        # enter your config values in here
        $provide.value('WEBSOCKET_DOMAIN', document.location.host)
        $provide.value('WEBSOCKET_PATH', '/websocket')
        $provide.value('WEBSOCKET_SSL', false)
        $provide.value('PING_TIMEOUT', 5000)
        return

app.run ($rootScope) ->

$(document).ready ->
    SyntaxHighlighter.defaults.collapse = true
