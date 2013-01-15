angular.module('WebChat').factory '_ChannelModel', ['_Model', (_Model) ->

    class ChannelModel extends _Model

        constructor: () ->
            super('channel')

        create: (item) ->
            super( @enhance(item) )


        update: (item) ->
            super( @enhance(item) )


        enhance: (item) ->
            item.mod = [1]
            item.autoScroll = true
            item.isUserMod = (userId) ->
                for id in @mod
                    if id == userId
                        return true
                return false

            return item


    return ChannelModel
]
