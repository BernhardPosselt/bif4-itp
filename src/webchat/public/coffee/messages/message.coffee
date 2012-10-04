webchat = require('webchat')

webchat.Message = class

    constructor: (@type) ->


    serialize: (data) ->
        message =
            type: @type
            data: data
        return message

