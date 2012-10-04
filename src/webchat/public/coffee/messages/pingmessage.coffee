#<<messages/message

class PingMessage extends Message

    constructor: () ->
        super('ping')


    serialize: ->
        data = {}
        return super(data)            

