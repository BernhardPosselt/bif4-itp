angular.module('WebChat').filter 'groupInChannel', ->
    return (groups, channel) ->
        result = []
        if channel == undefined or channel == null or channel.groups == undefined
            return result
        for group in groups
            if group.id in channel.groups
                result.push(group)
        return result