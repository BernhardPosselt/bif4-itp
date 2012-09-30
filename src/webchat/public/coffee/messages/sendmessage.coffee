window.WebChat or= {}

class SendMessage extends window.WebChat.Message


    constructor: (@text, @type, @channelId) ->
        super('message')


    serialize: ->
        data = 
            'message': @text
            'type': @type
            'channel_id': @channelId
        return super(data)            


window.WebChat.SendMessage = SendMessage