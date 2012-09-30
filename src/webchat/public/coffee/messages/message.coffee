window.WebChat or= {}

window.WebChat.Message = class

    constructor: (@type) ->


    serialize: (data) ->
        message =
            type: @type
            data: data
        return message