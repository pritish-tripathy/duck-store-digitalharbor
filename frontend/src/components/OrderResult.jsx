function OrderResult({ order }) {

    if (!order) {
        return null;
    }

    return (
        <div className="order-result">

            <div className="result-summary">

                <h3>
                    Order Summary
                </h3>

                <span className="result-total-label">
                    FINAL TOTAL
                </span>

                <div className="result-total">
                    ${Number(order.total).toFixed(2)}
                </div>


                <div className="result-info">

                    <span>
                        PACKAGE
                    </span>

                    <strong>
                        {order.packageType}
                    </strong>

                </div>


                <div className="result-info">

                    <span>
                        PROTECTION
                    </span>

                    <strong>
                        {order.protectionTypes.join(" + ")}
                    </strong>

                </div>

            </div>


            <div>

                <h3 className="breakdown-title">
                    Price Breakdown
                </h3>

                <table className="breakdown-table">

                    <thead>

                        <tr>
                            <th>Description</th>
                            <th>Amount</th>
                        </tr>

                    </thead>

                    <tbody>

                        {order.priceBreakdown.map(
                            (item, index) => (

                                <tr key={index}>

                                    <td>
                                        {item.description}
                                    </td>

                                    <td>
                                        {Number(item.amount) < 0
                                            ? `-$${Math.abs(Number(item.amount)).toFixed(2)}`
                                            : `+$${Number(item.amount).toFixed(2)}`
                                        }
                                    </td>

                                </tr>

                            )
                        )}

                    </tbody>

                </table>

            </div>

        </div>
    );
}

export default OrderResult;