angular.module('WebChat').filter 'groupInChannel', ->
    return (groups, args) ->
        result = []
        for group in groups
            for channelGroupId in args.groups
                if group.id == channelGroupId
                    result.push(group)
        return result