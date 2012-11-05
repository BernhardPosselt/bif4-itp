angular.module('WebChat').directive 'autoScroll',
['ChannelModel', 'ActiveChannel',
(ChannelModel, ActiveChannel) ->

    autoScrollDirective = 
        restrict: 'A'
        link: (scope, elm, attr) ->
            channel = ChannelModel.getItemById(ActiveChannel.getActiveChannelId())
            if channel.autoScroll
                $elem = $(elm)
                if $elem.hasClass('line')
                    stream = $elem.parent().parent().parent()
                else
                    stream = $elem.parent()
                stream.scrollTop(stream.prop("scrollHeight"))

    return autoScrollDirective
]

