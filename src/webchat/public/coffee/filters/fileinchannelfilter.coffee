angular.module('WebChat').filter 'fileInChannel', ->
    return (files, channel) ->
        result = []
        if channel == undefined or channel == null or channel.files == undefined
            return result
        for file in files
            if file.id in channel.files
                result.push(file)
        return result