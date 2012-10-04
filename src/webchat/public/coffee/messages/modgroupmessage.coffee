angular.module('WebChat').factory '_ModGroupMessage', ['_Message', (_Message)->

    class ModGroupMessage extends _Message

        constructor: (@groupId, @channelId, @value) ->
            super('modgroup')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @groupId
                value: @value
            return super(data)


    return ModGroupMessage

]