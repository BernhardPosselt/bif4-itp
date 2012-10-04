Message = require('webchat.Message')

class JoinMessage extends Message

    constructor: (@id) ->
        super('join')


    serialize: ->
        data = 
            id: @id
        return super(data)


webchat.JoinMessage = JoinMessage