window.WebChat or= {}

class PingMessage extends window.WebChat.Message

    constructor: () ->
        super('ping')


    serialize: ->
        data = {}
        return super(data)            


window.WebChat.PingMessage = PingMessage