package kacper.litwinow.qponycodingtaskandroid.api

object MocksApi {

    const val latestMockApiPath =
        "/latest?access_key=f73f15f9f02ab0e89fbad0f867a7420f&symbols=USD%2C%20AUD%2C%20CAD%2C%20PLN"

    const val historicalMockApiPath =
        "/2022-05-22?access_key=f73f15f9f02ab0e89fbad0f867a7420f&symbols=USD%2C%20AUD%2C%20CAD%2C%20PLN"
    const val mockApi = """
        {
        "success":true,
        "timestamp":1587734561,
        "base":"EUR",
        "date":"2022-05-22",
        "rates":{
        "AUD":1.502456,
        "CAD":1.366870,
        "PLN":4.632246,
        "USD":1.067331
    }
    }
"""
}
