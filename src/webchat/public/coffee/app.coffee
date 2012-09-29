angular.module('WebChat', []).
    config ($provide) ->
        # enter your config values in here
        $provide.value('websocket_domain', document.location.host)
        $provide.value('websocket_path', '/websocket')
        $provide.value('websocket_ssl', false)
        return