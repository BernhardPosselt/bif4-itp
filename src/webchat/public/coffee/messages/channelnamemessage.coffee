angular.module('WebChat').factory '_ChangeChannelNameMessage', ['_Message', (_Message)->

    class  ChangeChannelNameMessage extends _Message

        constructor: (@id, @name) ->
            super('channelname')


        serialize: ->
            data = 
                id: @id
                name: @name
            return super(data)


    return ChangeChannelNameMessage

]