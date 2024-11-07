import type { FC } from 'react';

interface ShlinkLogoProps {
    className?: string;
}

export const Logo: FC<ShlinkLogoProps> = () => {
    return (
        (
            <svg width="92px"
                 height="33px" viewBox="0 0 117 46" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M104.478 9.20043H76.9945V15.3041H87.6889V36.8037H93.7839V15.3041H104.478V9.20043Z"
                      fill="black"/>
                <path d="M116.492 9.10464H110.213V15.2137V36.7321H116.492V9.10464Z" fill="black"/>
                <path
                    d="M27.4838 18.3566H45.7005V27.6716H27.4838V45.9827H18.2168V27.6716H0L0 18.3566H18.2168V0.0455493H27.4838V18.3566V18.3566ZM54.9449 0V45.9827H64.2119V0H54.9449Z"
                    fill="#00AAE6"/>
            </svg>
        )
    )
};
