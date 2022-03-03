# HTTP GET request

[https://cloud.google.com/workflows/docs/samples/workflows-http-get](https://cloud.google.com/workflows/docs/samples/workflows-http-get)

Standard GET request

```
- get_message:
    call: http.get
    args:
      url: https://example.com/endpoint
      query:
        some_val: "Hello World"
        another_val: 123
    result: the_message

- return_value:
    return: ${the_message.body}
```

GET request with custom header and without query parameters

```
- get_message:
    call: http.get
    args:
      url: https://example.com/endpoint
      headers:
        Content-Type: "text/plain"
    result: the_message

- return_value:
    return: ${the_message.body}
```