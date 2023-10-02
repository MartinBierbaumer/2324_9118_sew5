package RIP;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subnet implements Comparable<Subnet> {
    int network;
    int mask;

    static Pattern IP = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})(/((\\d{1,2})|((\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3}))))?");

    private int getAddress(Matcher matcher, int index) {
        int address = 0;
        for (int i = 0; i < 4; i++) {
            int oktett = Integer.parseInt(matcher.group(i + index));
            if (oktett < 0 || oktett > 255) throw new IllegalArgumentException("Oktette können nur Werte von 0 bis 255 annehmen");
            address |= oktett << (8 * (3 - i));
        }
        return address;
    }

    public Subnet(String net) {
        Matcher matcher = IP.matcher(net);

        if (matcher.matches()) {
            network = getAddress(matcher, 1);

            if (matcher.group(6) == null) {
                mask = 0xFFFFFFFF;
            } else {
                if (matcher.group(7) != null) {
                    mask = -1 << (32 - Integer.parseInt(matcher.group(7)));
                } else {
                    mask = getAddress(matcher, 9);
                    if ((mask & (~mask >> 1)) != 0) throw new IllegalArgumentException("Falsche Subnetmask");
                }
            }
        } else {
            throw new IllegalArgumentException("Falsches Format");
        }
    }

    public Subnet(int network, int prefixLen) {
        this.network = network;
        this.mask = -1 << (32 - prefixLen);
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Subnet otherNet)) return false;
        return this.mask == otherNet.mask && (this.network & this.mask) == (otherNet.network & otherNet.mask);
    }

    @Override
    public int compareTo(Subnet o) {
        return Integer.compare(network & mask, o.network & o.mask);
    }
}
