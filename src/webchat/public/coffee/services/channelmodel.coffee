angular.module('WebChat').factory '_ChannelModel', ['_Model', (_Model) ->

    class ChannelModel extends _Model

        constructor: () ->
            super('channel')

        create: (item) ->
            item.autoScroll = true
            super(item)

    return ChannelModel
]
