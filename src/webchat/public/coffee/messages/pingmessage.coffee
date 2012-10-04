Message = require('webchat.Message')

class PingMessage extends Message

    constructor: () ->
        super('ping')


    serialize: ->
        data = {}
        return super(data)            


webchat.PingMessage = PingMessage