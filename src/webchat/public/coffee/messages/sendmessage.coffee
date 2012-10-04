Message = require('webchat.Message')

class SendMessage extends Message


    constructor: (@text, @type, @channelId) ->
        super('message')


    serialize: ->
        data = 
            'message': @text
            'type': @type
            'channel_id': @channelId
        return super(data)            


webchat.SendMessage = SendMessage