angular.module('WebChat').factory '_CloseChannelMessage', ['_Message', (_Message)->

    class  CloseChannelMessage extends _Message

        constructor: (@id) ->
            super('channelclose')


        serialize: ->
            data = 
                id: @id
            return super(data)


    return CloseChannelMessage

]