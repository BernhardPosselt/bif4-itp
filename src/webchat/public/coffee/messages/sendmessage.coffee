#<<messages/message

class SendMessage extends Message


    constructor: (@text, @type, @channelId) ->
        super('message')


    serialize: ->
        data = 
            'message': @text
            'type': @type
            'channel_id': @channelId
        return super(data)            

