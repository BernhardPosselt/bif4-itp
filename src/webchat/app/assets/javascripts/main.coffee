$(document).ready ->
  channels =
    "0":
      "name": "channel1",
      "topic": "some random topic",
      "files": [0, 1],
      "users": [0, 1],
      "groups": [0,2]

  chan = new ChannelManager()
  chan.update(channels)

